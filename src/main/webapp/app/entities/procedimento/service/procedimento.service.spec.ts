import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IProcedimento } from '../procedimento.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../procedimento.test-samples';

import { ProcedimentoService } from './procedimento.service';

const requireRestSample: IProcedimento = {
  ...sampleWithRequiredData,
};

describe('Procedimento Service', () => {
  let service: ProcedimentoService;
  let httpMock: HttpTestingController;
  let expectedResult: IProcedimento | IProcedimento[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ProcedimentoService);
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

    it('should create a Procedimento', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const procedimento = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(procedimento).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Procedimento', () => {
      const procedimento = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(procedimento).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Procedimento', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Procedimento', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Procedimento', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addProcedimentoToCollectionIfMissing', () => {
      it('should add a Procedimento to an empty array', () => {
        const procedimento: IProcedimento = sampleWithRequiredData;
        expectedResult = service.addProcedimentoToCollectionIfMissing([], procedimento);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(procedimento);
      });

      it('should not add a Procedimento to an array that contains it', () => {
        const procedimento: IProcedimento = sampleWithRequiredData;
        const procedimentoCollection: IProcedimento[] = [
          {
            ...procedimento,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addProcedimentoToCollectionIfMissing(procedimentoCollection, procedimento);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Procedimento to an array that doesn't contain it", () => {
        const procedimento: IProcedimento = sampleWithRequiredData;
        const procedimentoCollection: IProcedimento[] = [sampleWithPartialData];
        expectedResult = service.addProcedimentoToCollectionIfMissing(procedimentoCollection, procedimento);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(procedimento);
      });

      it('should add only unique Procedimento to an array', () => {
        const procedimentoArray: IProcedimento[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const procedimentoCollection: IProcedimento[] = [sampleWithRequiredData];
        expectedResult = service.addProcedimentoToCollectionIfMissing(procedimentoCollection, ...procedimentoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const procedimento: IProcedimento = sampleWithRequiredData;
        const procedimento2: IProcedimento = sampleWithPartialData;
        expectedResult = service.addProcedimentoToCollectionIfMissing([], procedimento, procedimento2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(procedimento);
        expect(expectedResult).toContain(procedimento2);
      });

      it('should accept null and undefined values', () => {
        const procedimento: IProcedimento = sampleWithRequiredData;
        expectedResult = service.addProcedimentoToCollectionIfMissing([], null, procedimento, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(procedimento);
      });

      it('should return initial array if no Procedimento is added', () => {
        const procedimentoCollection: IProcedimento[] = [sampleWithRequiredData];
        expectedResult = service.addProcedimentoToCollectionIfMissing(procedimentoCollection, undefined, null);
        expect(expectedResult).toEqual(procedimentoCollection);
      });
    });

    describe('compareProcedimento', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareProcedimento(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareProcedimento(entity1, entity2);
        const compareResult2 = service.compareProcedimento(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareProcedimento(entity1, entity2);
        const compareResult2 = service.compareProcedimento(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareProcedimento(entity1, entity2);
        const compareResult2 = service.compareProcedimento(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
