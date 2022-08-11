import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IDentista, NewDentista } from '../dentista.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDentista for edit and NewDentistaFormGroupInput for create.
 */
type DentistaFormGroupInput = IDentista | PartialWithRequiredKeyOf<NewDentista>;

type DentistaFormDefaults = Pick<NewDentista, 'id'>;

type DentistaFormGroupContent = {
  id: FormControl<IDentista['id'] | NewDentista['id']>;
  nomeDentista: FormControl<IDentista['nomeDentista']>;
  cpf: FormControl<IDentista['cpf']>;
  cro: FormControl<IDentista['cro']>;
  dataNascimento: FormControl<IDentista['dataNascimento']>;
  logradouro: FormControl<IDentista['logradouro']>;
  numero: FormControl<IDentista['numero']>;
  bairro: FormControl<IDentista['bairro']>;
  cidade: FormControl<IDentista['cidade']>;
  uf: FormControl<IDentista['uf']>;
  complemento: FormControl<IDentista['complemento']>;
  email: FormControl<IDentista['email']>;
  telefone: FormControl<IDentista['telefone']>;
};

export type DentistaFormGroup = FormGroup<DentistaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DentistaFormService {
  createDentistaFormGroup(dentista: DentistaFormGroupInput = { id: null }): DentistaFormGroup {
    const dentistaRawValue = {
      ...this.getFormDefaults(),
      ...dentista,
    };
    return new FormGroup<DentistaFormGroupContent>({
      id: new FormControl(
        { value: dentistaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      nomeDentista: new FormControl(dentistaRawValue.nomeDentista, {
        validators: [Validators.required],
      }),
      cpf: new FormControl(dentistaRawValue.cpf, {
        validators: [Validators.required],
      }),
      cro: new FormControl(dentistaRawValue.cro, {
        validators: [Validators.required],
      }),
      dataNascimento: new FormControl(dentistaRawValue.dataNascimento, {
        validators: [Validators.required],
      }),
      logradouro: new FormControl(dentistaRawValue.logradouro, {
        validators: [Validators.required],
      }),
      numero: new FormControl(dentistaRawValue.numero, {
        validators: [Validators.required],
      }),
      bairro: new FormControl(dentistaRawValue.bairro, {
        validators: [Validators.required],
      }),
      cidade: new FormControl(dentistaRawValue.cidade, {
        validators: [Validators.required],
      }),
      uf: new FormControl(dentistaRawValue.uf, {
        validators: [Validators.required],
      }),
      complemento: new FormControl(dentistaRawValue.complemento),
      email: new FormControl(dentistaRawValue.email, {
        validators: [Validators.required],
      }),
      telefone: new FormControl(dentistaRawValue.telefone, {
        validators: [Validators.required],
      }),
    });
  }

  getDentista(form: DentistaFormGroup): IDentista | NewDentista {
    return form.getRawValue() as IDentista | NewDentista;
  }

  resetForm(form: DentistaFormGroup, dentista: DentistaFormGroupInput): void {
    const dentistaRawValue = { ...this.getFormDefaults(), ...dentista };
    form.reset(
      {
        ...dentistaRawValue,
        id: { value: dentistaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): DentistaFormDefaults {
    return {
      id: null,
    };
  }
}
