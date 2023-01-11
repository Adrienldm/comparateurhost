import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IServeur, NewServeur } from '../serveur.model';

export type PartialUpdateServeur = Partial<IServeur> & Pick<IServeur, 'id'>;

export type EntityResponseType = HttpResponse<IServeur>;
export type EntityArrayResponseType = HttpResponse<IServeur[]>;

@Injectable({ providedIn: 'root' })
export class ServeurService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/serveurs');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(serveur: NewServeur): Observable<EntityResponseType> {
    return this.http.post<IServeur>(this.resourceUrl, serveur, { observe: 'response' });
  }

  update(serveur: IServeur): Observable<EntityResponseType> {
    return this.http.put<IServeur>(`${this.resourceUrl}/${this.getServeurIdentifier(serveur)}`, serveur, { observe: 'response' });
  }

  partialUpdate(serveur: PartialUpdateServeur): Observable<EntityResponseType> {
    return this.http.patch<IServeur>(`${this.resourceUrl}/${this.getServeurIdentifier(serveur)}`, serveur, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IServeur>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IServeur[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getServeurIdentifier(serveur: Pick<IServeur, 'id'>): number {
    return serveur.id;
  }

  compareServeur(o1: Pick<IServeur, 'id'> | null, o2: Pick<IServeur, 'id'> | null): boolean {
    return o1 && o2 ? this.getServeurIdentifier(o1) === this.getServeurIdentifier(o2) : o1 === o2;
  }

  addServeurToCollectionIfMissing<Type extends Pick<IServeur, 'id'>>(
    serveurCollection: Type[],
    ...serveursToCheck: (Type | null | undefined)[]
  ): Type[] {
    const serveurs: Type[] = serveursToCheck.filter(isPresent);
    if (serveurs.length > 0) {
      const serveurCollectionIdentifiers = serveurCollection.map(serveurItem => this.getServeurIdentifier(serveurItem)!);
      const serveursToAdd = serveurs.filter(serveurItem => {
        const serveurIdentifier = this.getServeurIdentifier(serveurItem);
        if (serveurCollectionIdentifiers.includes(serveurIdentifier)) {
          return false;
        }
        serveurCollectionIdentifiers.push(serveurIdentifier);
        return true;
      });
      return [...serveursToAdd, ...serveurCollection];
    }
    return serveurCollection;
  }
}
