import { IProcedimento, NewProcedimento } from './procedimento.model';

export const sampleWithRequiredData: IProcedimento = {
  id: 41850,
  descricao: 'Swedish copying withdrawal',
  valor: 53808,
};

export const sampleWithPartialData: IProcedimento = {
  id: 60852,
  descricao: 'action-items',
  valor: 3164,
};

export const sampleWithFullData: IProcedimento = {
  id: 13357,
  descricao: 'Shoes engage user-facing',
  valor: 81951,
};

export const sampleWithNewData: NewProcedimento = {
  descricao: 'synthesize',
  valor: 35828,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
