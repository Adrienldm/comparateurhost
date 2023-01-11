export interface IServeur {
  id: number;
  nomHebergeur?: string | null;
  nomServeur?: string | null;
  arch?: string | null;
  cpuNombre?: number | null;
  ram?: number | null;
  maxSire?: number | null;
  type?: string | null;
  priceMonthly?: number | null;
  hourlyPrice?: number | null;
  ipv6?: boolean | null;
  bandWidthInternal?: number | null;
  bandWidthExternal?: number | null;
}

export type NewServeur = Omit<IServeur, 'id'> & { id: null };
