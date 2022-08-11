export interface IProcedimento {
  id: number;
  descricao?: string | null;
  valor?: number | null;
}

export type NewProcedimento = Omit<IProcedimento, 'id'> & { id: null };
