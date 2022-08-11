import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IAtendimento, NewAtendimento } from '../atendimento.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAtendimento for edit and NewAtendimentoFormGroupInput for create.
 */
type AtendimentoFormGroupInput = IAtendimento | PartialWithRequiredKeyOf<NewAtendimento>;

type AtendimentoFormDefaults = Pick<NewAtendimento, 'id' | 'procedimentos'>;

type AtendimentoFormGroupContent = {
  id: FormControl<IAtendimento['id'] | NewAtendimento['id']>;
  dataAtendimento: FormControl<IAtendimento['dataAtendimento']>;
  procedimentos: FormControl<IAtendimento['procedimentos']>;
  dentista: FormControl<IAtendimento['dentista']>;
  paciente: FormControl<IAtendimento['paciente']>;
};

export type AtendimentoFormGroup = FormGroup<AtendimentoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AtendimentoFormService {
  createAtendimentoFormGroup(atendimento: AtendimentoFormGroupInput = { id: null }): AtendimentoFormGroup {
    const atendimentoRawValue = {
      ...this.getFormDefaults(),
      ...atendimento,
    };
    return new FormGroup<AtendimentoFormGroupContent>({
      id: new FormControl(
        { value: atendimentoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      dataAtendimento: new FormControl(atendimentoRawValue.dataAtendimento, {
        validators: [Validators.required],
      }),
      procedimentos: new FormControl(atendimentoRawValue.procedimentos ?? []),
      dentista: new FormControl(atendimentoRawValue.dentista),
      paciente: new FormControl(atendimentoRawValue.paciente),
    });
  }

  getAtendimento(form: AtendimentoFormGroup): IAtendimento | NewAtendimento {
    return form.getRawValue() as IAtendimento | NewAtendimento;
  }

  resetForm(form: AtendimentoFormGroup, atendimento: AtendimentoFormGroupInput): void {
    const atendimentoRawValue = { ...this.getFormDefaults(), ...atendimento };
    form.reset(
      {
        ...atendimentoRawValue,
        id: { value: atendimentoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): AtendimentoFormDefaults {
    return {
      id: null,
      procedimentos: [],
    };
  }
}
