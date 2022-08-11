import dayjs from 'dayjs/esm';

import { IAtendimento, NewAtendimento } from './atendimento.model';

export const sampleWithRequiredData: IAtendimento = {
  id: 15892,
  dataAtendimento: dayjs('2022-08-10'),
};

export const sampleWithPartialData: IAtendimento = {
  id: 8984,
  dataAtendimento: dayjs('2022-08-10'),
};

export const sampleWithFullData: IAtendimento = {
  id: 69051,
  dataAtendimento: dayjs('2022-08-10'),
};

export const sampleWithNewData: NewAtendimento = {
  dataAtendimento: dayjs('2022-08-10'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
