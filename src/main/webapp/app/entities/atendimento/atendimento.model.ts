import dayjs from 'dayjs/esm';
import { IProcedimento } from 'app/entities/procedimento/procedimento.model';
import { IDentista } from 'app/entities/dentista/dentista.model';
import { IPaciente } from 'app/entities/paciente/paciente.model';

export interface IAtendimento {
  id: number;
  dataAtendimento?: dayjs.Dayjs | null;
  procedimentos?: Pick<IProcedimento, 'id' | 'descricao'>[] | null;
  dentista?: Pick<IDentista, 'id' | 'nomeDentista'> | null;
  paciente?: Pick<IPaciente, 'id' | 'nomePaciente'> | null;
}

export type NewAtendimento = Omit<IAtendimento, 'id'> & { id: null };
