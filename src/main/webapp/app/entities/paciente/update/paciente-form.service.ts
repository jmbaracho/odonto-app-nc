import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IPaciente, NewPaciente } from '../paciente.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPaciente for edit and NewPacienteFormGroupInput for create.
 */
type PacienteFormGroupInput = IPaciente | PartialWithRequiredKeyOf<NewPaciente>;

type PacienteFormDefaults = Pick<NewPaciente, 'id'>;

type PacienteFormGroupContent = {
  id: FormControl<IPaciente['id'] | NewPaciente['id']>;
  nomePaciente: FormControl<IPaciente['nomePaciente']>;
  cpf: FormControl<IPaciente['cpf']>;
  dataNascimento: FormControl<IPaciente['dataNascimento']>;
  logradouro: FormControl<IPaciente['logradouro']>;
  numero: FormControl<IPaciente['numero']>;
  bairro: FormControl<IPaciente['bairro']>;
  cidade: FormControl<IPaciente['cidade']>;
  uf: FormControl<IPaciente['uf']>;
  complemento: FormControl<IPaciente['complemento']>;
  email: FormControl<IPaciente['email']>;
  telefone: FormControl<IPaciente['telefone']>;
};

export type PacienteFormGroup = FormGroup<PacienteFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PacienteFormService {
  createPacienteFormGroup(paciente: PacienteFormGroupInput = { id: null }): PacienteFormGroup {
    const pacienteRawValue = {
      ...this.getFormDefaults(),
      ...paciente,
    };
    return new FormGroup<PacienteFormGroupContent>({
      id: new FormControl(
        { value: pacienteRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      nomePaciente: new FormControl(pacienteRawValue.nomePaciente, {
        validators: [Validators.required],
      }),
      cpf: new FormControl(pacienteRawValue.cpf, {
        validators: [Validators.required],
      }),
      dataNascimento: new FormControl(pacienteRawValue.dataNascimento, {
        validators: [Validators.required],
      }),
      logradouro: new FormControl(pacienteRawValue.logradouro, {
        validators: [Validators.required],
      }),
      numero: new FormControl(pacienteRawValue.numero, {
        validators: [Validators.required],
      }),
      bairro: new FormControl(pacienteRawValue.bairro, {
        validators: [Validators.required],
      }),
      cidade: new FormControl(pacienteRawValue.cidade, {
        validators: [Validators.required],
      }),
      uf: new FormControl(pacienteRawValue.uf, {
        validators: [Validators.required],
      }),
      complemento: new FormControl(pacienteRawValue.complemento),
      email: new FormControl(pacienteRawValue.email, {
        validators: [Validators.required],
      }),
      telefone: new FormControl(pacienteRawValue.telefone, {
        validators: [Validators.required],
      }),
    });
  }

  getPaciente(form: PacienteFormGroup): IPaciente | NewPaciente {
    return form.getRawValue() as IPaciente | NewPaciente;
  }

  resetForm(form: PacienteFormGroup, paciente: PacienteFormGroupInput): void {
    const pacienteRawValue = { ...this.getFormDefaults(), ...paciente };
    form.reset(
      {
        ...pacienteRawValue,
        id: { value: pacienteRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): PacienteFormDefaults {
    return {
      id: null,
    };
  }
}
