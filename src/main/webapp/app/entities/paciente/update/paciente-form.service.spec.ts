import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../paciente.test-samples';

import { PacienteFormService } from './paciente-form.service';

describe('Paciente Form Service', () => {
  let service: PacienteFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PacienteFormService);
  });

  describe('Service methods', () => {
    describe('createPacienteFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPacienteFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nomePaciente: expect.any(Object),
            cpf: expect.any(Object),
            dataNascimento: expect.any(Object),
            logradouro: expect.any(Object),
            numero: expect.any(Object),
            bairro: expect.any(Object),
            cidade: expect.any(Object),
            uf: expect.any(Object),
            complemento: expect.any(Object),
            email: expect.any(Object),
            telefone: expect.any(Object),
          })
        );
      });

      it('passing IPaciente should create a new form with FormGroup', () => {
        const formGroup = service.createPacienteFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nomePaciente: expect.any(Object),
            cpf: expect.any(Object),
            dataNascimento: expect.any(Object),
            logradouro: expect.any(Object),
            numero: expect.any(Object),
            bairro: expect.any(Object),
            cidade: expect.any(Object),
            uf: expect.any(Object),
            complemento: expect.any(Object),
            email: expect.any(Object),
            telefone: expect.any(Object),
          })
        );
      });
    });

    describe('getPaciente', () => {
      it('should return NewPaciente for default Paciente initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createPacienteFormGroup(sampleWithNewData);

        const paciente = service.getPaciente(formGroup) as any;

        expect(paciente).toMatchObject(sampleWithNewData);
      });

      it('should return NewPaciente for empty Paciente initial value', () => {
        const formGroup = service.createPacienteFormGroup();

        const paciente = service.getPaciente(formGroup) as any;

        expect(paciente).toMatchObject({});
      });

      it('should return IPaciente', () => {
        const formGroup = service.createPacienteFormGroup(sampleWithRequiredData);

        const paciente = service.getPaciente(formGroup) as any;

        expect(paciente).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPaciente should not enable id FormControl', () => {
        const formGroup = service.createPacienteFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPaciente should disable id FormControl', () => {
        const formGroup = service.createPacienteFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
