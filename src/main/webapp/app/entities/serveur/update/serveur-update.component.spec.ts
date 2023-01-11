import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ServeurFormService } from './serveur-form.service';
import { ServeurService } from '../service/serveur.service';
import { IServeur } from '../serveur.model';

import { ServeurUpdateComponent } from './serveur-update.component';

describe('Serveur Management Update Component', () => {
  let comp: ServeurUpdateComponent;
  let fixture: ComponentFixture<ServeurUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let serveurFormService: ServeurFormService;
  let serveurService: ServeurService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ServeurUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(ServeurUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ServeurUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    serveurFormService = TestBed.inject(ServeurFormService);
    serveurService = TestBed.inject(ServeurService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const serveur: IServeur = { id: 456 };

      activatedRoute.data = of({ serveur });
      comp.ngOnInit();

      expect(comp.serveur).toEqual(serveur);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IServeur>>();
      const serveur = { id: 123 };
      jest.spyOn(serveurFormService, 'getServeur').mockReturnValue(serveur);
      jest.spyOn(serveurService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ serveur });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: serveur }));
      saveSubject.complete();

      // THEN
      expect(serveurFormService.getServeur).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(serveurService.update).toHaveBeenCalledWith(expect.objectContaining(serveur));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IServeur>>();
      const serveur = { id: 123 };
      jest.spyOn(serveurFormService, 'getServeur').mockReturnValue({ id: null });
      jest.spyOn(serveurService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ serveur: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: serveur }));
      saveSubject.complete();

      // THEN
      expect(serveurFormService.getServeur).toHaveBeenCalled();
      expect(serveurService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IServeur>>();
      const serveur = { id: 123 };
      jest.spyOn(serveurService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ serveur });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(serveurService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
