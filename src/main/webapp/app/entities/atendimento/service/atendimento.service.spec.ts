import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IAtendimento } from '../atendimento.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../atendimento.test-samples';

import { AtendimentoService, RestAtendimento } from './atendimento.service';

const requireRestSample: RestAtendimento = {
  ...sampleWithRequiredData,
  dataAtendimento: sampleWithRequiredData.dataAtendimento?.format(DATE_FORMAT),
};

describe('Atendimento Service', () => {
  let service: AtendimentoService;
  let httpMock: HttpTestingController;
  let expectedResult: IAtendimento | IAtendimento[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(AtendimentoService);
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

    it('should create a Atendimento', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const atendimento = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(atendimento).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Atendimento', () => {
      const atendimento = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(atendimento).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Atendimento', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Atendimento', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Atendimento', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addAtendimentoToCollectionIfMissing', () => {
      it('should add a Atendimento to an empty array', () => {
        const atendimento: IAtendimento = sampleWithRequiredData;
        expectedResult = service.addAtendimentoToCollectionIfMissing([], atendimento);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(atendimento);
      });

      it('should not add a Atendimento to an array that contains it', () => {
        const atendimento: IAtendimento = sampleWithRequiredData;
        const atendimentoCollection: IAtendimento[] = [
          {
            ...atendimento,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addAtendimentoToCollectionIfMissing(atendimentoCollection, atendimento);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Atendimento to an array that doesn't contain it", () => {
        const atendimento: IAtendimento = sampleWithRequiredData;
        const atendimentoCollection: IAtendimento[] = [sampleWithPartialData];
        expectedResult = service.addAtendimentoToCollectionIfMissing(atendimentoCollection, atendimento);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(atendimento);
      });

      it('should add only unique Atendimento to an array', () => {
        const atendimentoArray: IAtendimento[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const atendimentoCollection: IAtendimento[] = [sampleWithRequiredData];
        expectedResult = service.addAtendimentoToCollectionIfMissing(atendimentoCollection, ...atendimentoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const atendimento: IAtendimento = sampleWithRequiredData;
        const atendimento2: IAtendimento = sampleWithPartialData;
        expectedResult = service.addAtendimentoToCollectionIfMissing([], atendimento, atendimento2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(atendimento);
        expect(expectedResult).toContain(atendimento2);
      });

      it('should accept null and undefined values', () => {
        const atendimento: IAtendimento = sampleWithRequiredData;
        expectedResult = service.addAtendimentoToCollectionIfMissing([], null, atendimento, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(atendimento);
      });

      it('should return initial array if no Atendimento is added', () => {
        const atendimentoCollection: IAtendimento[] = [sampleWithRequiredData];
        expectedResult = service.addAtendimentoToCollectionIfMissing(atendimentoCollection, undefined, null);
        expect(expectedResult).toEqual(atendimentoCollection);
      });
    });

    describe('compareAtendimento', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareAtendimento(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareAtendimento(entity1, entity2);
        const compareResult2 = service.compareAtendimento(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareAtendimento(entity1, entity2);
        const compareResult2 = service.compareAtendimento(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareAtendimento(entity1, entity2);
        const compareResult2 = service.compareAtendimento(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
