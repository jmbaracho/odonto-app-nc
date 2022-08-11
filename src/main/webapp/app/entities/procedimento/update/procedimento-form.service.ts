import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IProcedimento, NewProcedimento } from '../procedimento.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IProcedimento for edit and NewProcedimentoFormGroupInput for create.
 */
type ProcedimentoFormGroupInput = IProcedimento | PartialWithRequiredKeyOf<NewProcedimento>;

type ProcedimentoFormDefaults = Pick<NewProcedimento, 'id' | 'atendimentos'>;

type ProcedimentoFormGroupContent = {
  id: FormControl<IProcedimento['id'] | NewProcedimento['id']>;
  descricao: FormControl<IProcedimento['descricao']>;
  valor: FormControl<IProcedimento['valor']>;
  atendimentos: FormControl<IProcedimento['atendimentos']>;
};

export type ProcedimentoFormGroup = FormGroup<ProcedimentoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ProcedimentoFormService {
  createProcedimentoFormGroup(procedimento: ProcedimentoFormGroupInput = { id: null }): ProcedimentoFormGroup {
    const procedimentoRawValue = {
      ...this.getFormDefaults(),
      ...procedimento,
    };
    return new FormGroup<ProcedimentoFormGroupContent>({
      id: new FormControl(
        { value: procedimentoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      descricao: new FormControl(procedimentoRawValue.descricao, {
        validators: [Validators.required],
      }),
      valor: new FormControl(procedimentoRawValue.valor, {
        validators: [Validators.required],
      }),
      atendimentos: new FormControl(procedimentoRawValue.atendimentos ?? []),
    });
  }

  getProcedimento(form: ProcedimentoFormGroup): IProcedimento | NewProcedimento {
    return form.getRawValue() as IProcedimento | NewProcedimento;
  }

  resetForm(form: ProcedimentoFormGroup, procedimento: ProcedimentoFormGroupInput): void {
    const procedimentoRawValue = { ...this.getFormDefaults(), ...procedimento };
    form.reset(
      {
        ...procedimentoRawValue,
        id: { value: procedimentoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ProcedimentoFormDefaults {
    return {
      id: null,
      atendimentos: [],
    };
  }
}
