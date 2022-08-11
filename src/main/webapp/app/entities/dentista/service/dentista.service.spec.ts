import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IDentista } from '../dentista.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../dentista.test-samples';

import { DentistaService, RestDentista } from './dentista.service';

const requireRestSample: RestDentista = {
  ...sampleWithRequiredData,
  dataNascimento: sampleWithRequiredData.dataNascimento?.format(DATE_FORMAT),
};

describe('Dentista Service', () => {
  let service: DentistaService;
  let httpMock: HttpTestingController;
  let expectedResult: IDentista | IDentista[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(DentistaService);
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

    it('should create a Dentista', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const dentista = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(dentista).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Dentista', () => {
      const dentista = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(dentista).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Dentista', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Dentista', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Dentista', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addDentistaToCollectionIfMissing', () => {
      it('should add a Dentista to an empty array', () => {
        const dentista: IDentista = sampleWithRequiredData;
        expectedResult = service.addDentistaToCollectionIfMissing([], dentista);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(dentista);
      });

      it('should not add a Dentista to an array that contains it', () => {
        const dentista: IDentista = sampleWithRequiredData;
        const dentistaCollection: IDentista[] = [
          {
            ...dentista,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addDentistaToCollectionIfMissing(dentistaCollection, dentista);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Dentista to an array that doesn't contain it", () => {
        const dentista: IDentista = sampleWithRequiredData;
        const dentistaCollection: IDentista[] = [sampleWithPartialData];
        expectedResult = service.addDentistaToCollectionIfMissing(dentistaCollection, dentista);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(dentista);
      });

      it('should add only unique Dentista to an array', () => {
        const dentistaArray: IDentista[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const dentistaCollection: IDentista[] = [sampleWithRequiredData];
        expectedResult = service.addDentistaToCollectionIfMissing(dentistaCollection, ...dentistaArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const dentista: IDentista = sampleWithRequiredData;
        const dentista2: IDentista = sampleWithPartialData;
        expectedResult = service.addDentistaToCollectionIfMissing([], dentista, dentista2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(dentista);
        expect(expectedResult).toContain(dentista2);
      });

      it('should accept null and undefined values', () => {
        const dentista: IDentista = sampleWithRequiredData;
        expectedResult = service.addDentistaToCollectionIfMissing([], null, dentista, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(dentista);
      });

      it('should return initial array if no Dentista is added', () => {
        const dentistaCollection: IDentista[] = [sampleWithRequiredData];
        expectedResult = service.addDentistaToCollectionIfMissing(dentistaCollection, undefined, null);
        expect(expectedResult).toEqual(dentistaCollection);
      });
    });

    describe('compareDentista', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareDentista(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareDentista(entity1, entity2);
        const compareResult2 = service.compareDentista(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareDentista(entity1, entity2);
        const compareResult2 = service.compareDentista(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareDentista(entity1, entity2);
        const compareResult2 = service.compareDentista(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
