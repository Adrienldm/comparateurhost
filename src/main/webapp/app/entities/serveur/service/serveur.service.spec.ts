import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IServeur } from '../serveur.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../serveur.test-samples';

import { ServeurService } from './serveur.service';

const requireRestSample: IServeur = {
  ...sampleWithRequiredData,
};

describe('Serveur Service', () => {
  let service: ServeurService;
  let httpMock: HttpTestingController;
  let expectedResult: IServeur | IServeur[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ServeurService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a Serveur', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const serveur = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(serveur).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Serveur', () => {
      const serveur = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(serveur).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Serveur', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Serveur', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Serveur', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addServeurToCollectionIfMissing', () => {
      it('should add a Serveur to an empty array', () => {
        const serveur: IServeur = sampleWithRequiredData;
        expectedResult = service.addServeurToCollectionIfMissing([], serveur);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(serveur);
      });

      it('should not add a Serveur to an array that contains it', () => {
        const serveur: IServeur = sampleWithRequiredData;
        const serveurCollection: IServeur[] = [
          {
            ...serveur,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addServeurToCollectionIfMissing(serveurCollection, serveur);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Serveur to an array that doesn't contain it", () => {
        const serveur: IServeur = sampleWithRequiredData;
        const serveurCollection: IServeur[] = [sampleWithPartialData];
        expectedResult = service.addServeurToCollectionIfMissing(serveurCollection, serveur);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(serveur);
      });

      it('should add only unique Serveur to an array', () => {
        const serveurArray: IServeur[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const serveurCollection: IServeur[] = [sampleWithRequiredData];
        expectedResult = service.addServeurToCollectionIfMissing(serveurCollection, ...serveurArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const serveur: IServeur = sampleWithRequiredData;
        const serveur2: IServeur = sampleWithPartialData;
        expectedResult = service.addServeurToCollectionIfMissing([], serveur, serveur2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(serveur);
        expect(expectedResult).toContain(serveur2);
      });

      it('should accept null and undefined values', () => {
        const serveur: IServeur = sampleWithRequiredData;
        expectedResult = service.addServeurToCollectionIfMissing([], null, serveur, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(serveur);
      });

      it('should return initial array if no Serveur is added', () => {
        const serveurCollection: IServeur[] = [sampleWithRequiredData];
        expectedResult = service.addServeurToCollectionIfMissing(serveurCollection, undefined, null);
        expect(expectedResult).toEqual(serveurCollection);
      });
    });

    describe('compareServeur', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareServeur(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareServeur(entity1, entity2);
        const compareResult2 = service.compareServeur(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareServeur(entity1, entity2);
        const compareResult2 = service.compareServeur(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareServeur(entity1, entity2);
        const compareResult2 = service.compareServeur(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
