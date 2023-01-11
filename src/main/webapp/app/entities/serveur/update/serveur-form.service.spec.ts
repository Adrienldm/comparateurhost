import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../serveur.test-samples';

import { ServeurFormService } from './serveur-form.service';

describe('Serveur Form Service', () => {
  let service: ServeurFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ServeurFormService);
  });

  describe('Service methods', () => {
    describe('createServeurFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createServeurFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nomHebergeur: expect.any(Object),
            nomServeur: expect.any(Object),
            arch: expect.any(Object),
            cpuNombre: expect.any(Object),
            ram: expect.any(Object),
            maxSire: expect.any(Object),
            type: expect.any(Object),
            priceMonthly: expect.any(Object),
            hourlyPrice: expect.any(Object),
            ipv6: expect.any(Object),
            bandWidthInternal: expect.any(Object),
            bandWidthExternal: expect.any(Object),
          })
        );
      });

      it('passing IServeur should create a new form with FormGroup', () => {
        const formGroup = service.createServeurFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nomHebergeur: expect.any(Object),
            nomServeur: expect.any(Object),
            arch: expect.any(Object),
            cpuNombre: expect.any(Object),
            ram: expect.any(Object),
            maxSire: expect.any(Object),
            type: expect.any(Object),
            priceMonthly: expect.any(Object),
            hourlyPrice: expect.any(Object),
            ipv6: expect.any(Object),
            bandWidthInternal: expect.any(Object),
            bandWidthExternal: expect.any(Object),
          })
        );
      });
    });

    describe('getServeur', () => {
      it('should return NewServeur for default Serveur initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createServeurFormGroup(sampleWithNewData);

        const serveur = service.getServeur(formGroup) as any;

        expect(serveur).toMatchObject(sampleWithNewData);
      });

      it('should return NewServeur for empty Serveur initial value', () => {
        const formGroup = service.createServeurFormGroup();

        const serveur = service.getServeur(formGroup) as any;

        expect(serveur).toMatchObject({});
      });

      it('should return IServeur', () => {
        const formGroup = service.createServeurFormGroup(sampleWithRequiredData);

        const serveur = service.getServeur(formGroup) as any;

        expect(serveur).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IServeur should not enable id FormControl', () => {
        const formGroup = service.createServeurFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewServeur should disable id FormControl', () => {
        const formGroup = service.createServeurFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
