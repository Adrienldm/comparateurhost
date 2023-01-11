import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IServeur, NewServeur } from '../serveur.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IServeur for edit and NewServeurFormGroupInput for create.
 */
type ServeurFormGroupInput = IServeur | PartialWithRequiredKeyOf<NewServeur>;

type ServeurFormDefaults = Pick<NewServeur, 'id' | 'ipv6'>;

type ServeurFormGroupContent = {
  id: FormControl<IServeur['id'] | NewServeur['id']>;
  nomHebergeur: FormControl<IServeur['nomHebergeur']>;
  nomServeur: FormControl<IServeur['nomServeur']>;
  arch: FormControl<IServeur['arch']>;
  cpuNombre: FormControl<IServeur['cpuNombre']>;
  ram: FormControl<IServeur['ram']>;
  maxSize: FormControl<IServeur['maxSize']>;
  type: FormControl<IServeur['type']>;
  priceMonthly: FormControl<IServeur['priceMonthly']>;
  hourlyPrice: FormControl<IServeur['hourlyPrice']>;
  ipv6: FormControl<IServeur['ipv6']>;
  bandWidthInternal: FormControl<IServeur['bandWidthInternal']>;
  bandWidthExternal: FormControl<IServeur['bandWidthExternal']>;
};

export type ServeurFormGroup = FormGroup<ServeurFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ServeurFormService {
  createServeurFormGroup(serveur: ServeurFormGroupInput = { id: null }): ServeurFormGroup {
    const serveurRawValue = {
      ...this.getFormDefaults(),
      ...serveur,
    };
    return new FormGroup<ServeurFormGroupContent>({
      id: new FormControl(
        { value: serveurRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      nomHebergeur: new FormControl(serveurRawValue.nomHebergeur),
      nomServeur: new FormControl(serveurRawValue.nomServeur),
      arch: new FormControl(serveurRawValue.arch),
      cpuNombre: new FormControl(serveurRawValue.cpuNombre),
      ram: new FormControl(serveurRawValue.ram),
      maxSize: new FormControl(serveurRawValue.maxSize),
      type: new FormControl(serveurRawValue.type),
      priceMonthly: new FormControl(serveurRawValue.priceMonthly),
      hourlyPrice: new FormControl(serveurRawValue.hourlyPrice),
      ipv6: new FormControl(serveurRawValue.ipv6),
      bandWidthInternal: new FormControl(serveurRawValue.bandWidthInternal),
      bandWidthExternal: new FormControl(serveurRawValue.bandWidthExternal),
    });
  }

  getServeur(form: ServeurFormGroup): IServeur | NewServeur {
    return form.getRawValue() as IServeur | NewServeur;
  }

  resetForm(form: ServeurFormGroup, serveur: ServeurFormGroupInput): void {
    const serveurRawValue = { ...this.getFormDefaults(), ...serveur };
    form.reset(
      {
        ...serveurRawValue,
        id: { value: serveurRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ServeurFormDefaults {
    return {
      id: null,
      ipv6: false,
    };
  }
}
