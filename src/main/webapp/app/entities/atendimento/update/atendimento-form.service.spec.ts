import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../atendimento.test-samples';

import { AtendimentoFormService } from './atendimento-form.service';

describe('Atendimento Form Service', () => {
  let service: AtendimentoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AtendimentoFormService);
  });

  describe('Service methods', () => {
    describe('createAtendimentoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAtendimentoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            dataAtendimento: expect.any(Object),
            procedimento: expect.any(Object),
            dentista: expect.any(Object),
            paciente: expect.any(Object),
          })
        );
      });

      it('passing IAtendimento should create a new form with FormGroup', () => {
        const formGroup = service.createAtendimentoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            dataAtendimento: expect.any(Object),
            procedimento: expect.any(Object),
            dentista: expect.any(Object),
            paciente: expect.any(Object),
          })
        );
      });
    });

    describe('getAtendimento', () => {
      it('should return NewAtendimento for default Atendimento initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createAtendimentoFormGroup(sampleWithNewData);

        const atendimento = service.getAtendimento(formGroup) as any;

        expect(atendimento).toMatchObject(sampleWithNewData);
      });

      it('should return NewAtendimento for empty Atendimento initial value', () => {
        const formGroup = service.createAtendimentoFormGroup();

        const atendimento = service.getAtendimento(formGroup) as any;

        expect(atendimento).toMatchObject({});
      });

      it('should return IAtendimento', () => {
        const formGroup = service.createAtendimentoFormGroup(sampleWithRequiredData);

        const atendimento = service.getAtendimento(formGroup) as any;

        expect(atendimento).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IAtendimento should not enable id FormControl', () => {
        const formGroup = service.createAtendimentoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewAtendimento should disable id FormControl', () => {
        const formGroup = service.createAtendimentoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
