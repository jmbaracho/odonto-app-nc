import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../procedimento.test-samples';

import { ProcedimentoFormService } from './procedimento-form.service';

describe('Procedimento Form Service', () => {
  let service: ProcedimentoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProcedimentoFormService);
  });

  describe('Service methods', () => {
    describe('createProcedimentoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createProcedimentoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            descricao: expect.any(Object),
            valor: expect.any(Object),
          })
        );
      });

      it('passing IProcedimento should create a new form with FormGroup', () => {
        const formGroup = service.createProcedimentoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            descricao: expect.any(Object),
            valor: expect.any(Object),
          })
        );
      });
    });

    describe('getProcedimento', () => {
      it('should return NewProcedimento for default Procedimento initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createProcedimentoFormGroup(sampleWithNewData);

        const procedimento = service.getProcedimento(formGroup) as any;

        expect(procedimento).toMatchObject(sampleWithNewData);
      });

      it('should return NewProcedimento for empty Procedimento initial value', () => {
        const formGroup = service.createProcedimentoFormGroup();

        const procedimento = service.getProcedimento(formGroup) as any;

        expect(procedimento).toMatchObject({});
      });

      it('should return IProcedimento', () => {
        const formGroup = service.createProcedimentoFormGroup(sampleWithRequiredData);

        const procedimento = service.getProcedimento(formGroup) as any;

        expect(procedimento).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IProcedimento should not enable id FormControl', () => {
        const formGroup = service.createProcedimentoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewProcedimento should disable id FormControl', () => {
        const formGroup = service.createProcedimentoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
