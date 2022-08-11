import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../dentista.test-samples';

import { DentistaFormService } from './dentista-form.service';

describe('Dentista Form Service', () => {
  let service: DentistaFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DentistaFormService);
  });

  describe('Service methods', () => {
    describe('createDentistaFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createDentistaFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nomeDentista: expect.any(Object),
            cpf: expect.any(Object),
            cro: expect.any(Object),
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

      it('passing IDentista should create a new form with FormGroup', () => {
        const formGroup = service.createDentistaFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nomeDentista: expect.any(Object),
            cpf: expect.any(Object),
            cro: expect.any(Object),
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

    describe('getDentista', () => {
      it('should return NewDentista for default Dentista initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createDentistaFormGroup(sampleWithNewData);

        const dentista = service.getDentista(formGroup) as any;

        expect(dentista).toMatchObject(sampleWithNewData);
      });

      it('should return NewDentista for empty Dentista initial value', () => {
        const formGroup = service.createDentistaFormGroup();

        const dentista = service.getDentista(formGroup) as any;

        expect(dentista).toMatchObject({});
      });

      it('should return IDentista', () => {
        const formGroup = service.createDentistaFormGroup(sampleWithRequiredData);

        const dentista = service.getDentista(formGroup) as any;

        expect(dentista).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IDentista should not enable id FormControl', () => {
        const formGroup = service.createDentistaFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewDentista should disable id FormControl', () => {
        const formGroup = service.createDentistaFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
