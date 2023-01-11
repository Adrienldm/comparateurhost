import { IServeur, NewServeur } from './serveur.model';

export const sampleWithRequiredData: IServeur = {
  id: 26585,
};

export const sampleWithPartialData: IServeur = {
  id: 39861,
  nomHebergeur: 'Salad',
  nomServeur: 'Tugrik withdrawal',
  arch: 'XML lime withdrawal',
  maxSize: 83081,
  type: 'face Fantastic Gorgeous',
  priceMonthly: 9352,
  hourlyPrice: 90413,
  ipv6: false,
  bandWidthInternal: 52336,
  bandWidthExternal: 7268,
};

export const sampleWithFullData: IServeur = {
  id: 41784,
  nomHebergeur: 'Paraguay Shoes',
  nomServeur: 'Rubber',
  arch: 'Bosnie-Herzégovine',
  cpuNombre: 52228,
  ram: 27575,
  maxSize: 12818,
  type: 'monetize',
  priceMonthly: 55484,
  hourlyPrice: 84177,
  ipv6: true,
  bandWidthInternal: 68319,
  bandWidthExternal: 20624,
};

export const sampleWithNewData: NewServeur = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
