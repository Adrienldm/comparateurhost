import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ServeurService } from '../service/serveur.service';

import { ServeurComponent } from './serveur.component';

describe('Serveur Management Component', () => {
  let comp: ServeurComponent;
  let fixture: ComponentFixture<ServeurComponent>;
  let service: ServeurService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'serveur', component: ServeurComponent }]), HttpClientTestingModule],
      declarations: [ServeurComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({
              defaultSort: 'id,asc',
            }),
            queryParamMap: of(
              jest.requireActual('@angular/router').convertToParamMap({
                page: '1',
                size: '1',
                sort: 'id,desc',
              })
            ),
            snapshot: { queryParams: {} },
          },
        },
      ],
    })
      .overrideTemplate(ServeurComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ServeurComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(ServeurService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.serveurs?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to serveurService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getServeurIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getServeurIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
