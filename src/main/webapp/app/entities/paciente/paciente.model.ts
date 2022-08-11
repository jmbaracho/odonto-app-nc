import dayjs from 'dayjs/esm';

export interface IPaciente {
  id: number;
  nomePaciente?: string | null;
  cpf?: string | null;
  dataNascimento?: dayjs.Dayjs | null;
  logradouro?: string | null;
  numero?: string | null;
  bairro?: string | null;
  cidade?: string | null;
  uf?: string | null;
  complemento?: string | null;
  email?: string | null;
  telefone?: string | null;
}

export type NewPaciente = Omit<IPaciente, 'id'> & { id: null };
