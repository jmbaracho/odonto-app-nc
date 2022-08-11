import { IAtendimento } from 'app/entities/atendimento/atendimento.model';

export interface IProcedimento {
  id: number;
  descricao?: string | null;
  valor?: number | null;
  atendimentos?: Pick<IAtendimento, 'id'>[] | null;
}

export type NewProcedimento = Omit<IProcedimento, 'id'> & { id: null };
