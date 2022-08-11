import dayjs from 'dayjs/esm';

import { IDentista, NewDentista } from './dentista.model';

export const sampleWithRequiredData: IDentista = {
  id: 47728,
  nomeDentista: 'District',
  cpf: 'Gourde withdrawal AGP',
  cro: 'interactive Sports virtual',
  dataNascimento: dayjs('2022-08-10'),
  logradouro: 'Coordinator Avon Functionality',
  numero: 'Soft',
  bairro: 'hardware',
  cidade: 'harness reintermediate digital',
  uf: 'Bedfordshire Loan Andorra',
  email: 'Ricky.Balistreri42@hotmail.com',
  telefone: 'visualize Branding',
};

export const sampleWithPartialData: IDentista = {
  id: 17563,
  nomeDentista: 'Brand',
  cpf: 'parse secondary',
  cro: 'monetize azure Architect',
  dataNascimento: dayjs('2022-08-10'),
  logradouro: 'Handcrafted methodologies',
  numero: 'connect',
  bairro: 'Toys Customer',
  cidade: 'Metal',
  uf: 'Moldovan JSON',
  complemento: 'impactful Mouse',
  email: 'Woodrow_Waelchi@gmail.com',
  telefone: 'Concrete Jersey Borders',
};

export const sampleWithFullData: IDentista = {
  id: 33311,
  nomeDentista: 'invoice partnerships',
  cpf: 'turn-key Dollar deposit',
  cro: 'Kwanza compress',
  dataNascimento: dayjs('2022-08-10'),
  logradouro: 'back-end Corporate Place',
  numero: 'Manager',
  bairro: 'Chicken Fresh',
  cidade: 'Orchestrator',
  uf: 'solution-oriented',
  complemento: 'copying Steel',
  email: 'Maverick.Mertz37@hotmail.com',
  telefone: 'Consultant Analyst',
};

export const sampleWithNewData: NewDentista = {
  nomeDentista: 'parsing Business-focused',
  cpf: 'Versatile Illinois Loan',
  cro: 'driver',
  dataNascimento: dayjs('2022-08-10'),
  logradouro: 'Metrics silver',
  numero: 'compress',
  bairro: 'incentivize',
  cidade: 'Unbranded Checking',
  uf: 'panel',
  email: 'Mona_Bechtelar@gmail.com',
  telefone: 'connecting invoice',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
