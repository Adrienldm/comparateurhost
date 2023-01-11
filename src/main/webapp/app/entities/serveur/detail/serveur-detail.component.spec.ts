import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ServeurDetailComponent } from './serveur-detail.component';

describe('Serveur Management Detail Component', () => {
  let comp: ServeurDetailComponent;
  let fixture: ComponentFixture<ServeurDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ServeurDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ serveur: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ServeurDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ServeurDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load serveur on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.serveur).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
