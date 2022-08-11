import dayjs from 'dayjs/esm';

import { IPaciente, NewPaciente } from './paciente.model';

export const sampleWithRequiredData: IPaciente = {
  id: 20712,
  nomePaciente: 'Representative navigating array',
  cpf: 'Developer Cross-group',
  dataNascimento: dayjs('2022-08-10'),
  logradouro: 'deposit Sudanese Berkshire',
  numero: 'bypassing Electronics Games',
  bairro: 'seamless back-end niches',
  cidade: 'SQL copying',
  uf: 'Gloves',
  email: 'Patricia25@hotmail.com',
  telefone: 'Israel systemic',
};

export const sampleWithPartialData: IPaciente = {
  id: 69249,
  nomePaciente: 'Expanded index',
  cpf: 'transmitter SMTP',
  dataNascimento: dayjs('2022-08-10'),
  logradouro: 'invoice archive',
  numero: 'Intelligent',
  bairro: 'Bedfordshire',
  cidade: 'Soap Intelligent',
  uf: 'Rubber Card microchip',
  email: 'Tina_Trantow@yahoo.com',
  telefone: 'Namibia',
};

export const sampleWithFullData: IPaciente = {
  id: 6697,
  nomePaciente: 'Pound',
  cpf: 'grey',
  dataNascimento: dayjs('2022-08-10'),
  logradouro: 'Consultant',
  numero: 'yellow SSL Nebraska',
  bairro: 'Buckinghamshire',
  cidade: 'gold primary Administrator',
  uf: 'Borders instruction',
  complemento: 'Hat redundant Metal',
  email: 'Bertram_Oberbrunner@gmail.com',
  telefone: 'services National',
};

export const sampleWithNewData: NewPaciente = {
  nomePaciente: 'Keyboard bottom-line Assurance',
  cpf: 'synthesize matrix Port',
  dataNascimento: dayjs('2022-08-10'),
  logradouro: 'Plastic',
  numero: 'Credit methodologies Account',
  bairro: 'wireless Nevada even-keeled',
  cidade: 'blockchains Soap Dinar',
  uf: 'convergence eyeballs',
  email: 'Marcelina.Larson@gmail.com',
  telefone: 'invoice Handcrafted Solomon',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
