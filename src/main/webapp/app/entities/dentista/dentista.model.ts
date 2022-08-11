import dayjs from 'dayjs/esm';

export interface IDentista {
  id: number;
  nomeDentista?: string | null;
  cpf?: string | null;
  cro?: string | null;
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

export type NewDentista = Omit<IDentista, 'id'> & { id: null };
