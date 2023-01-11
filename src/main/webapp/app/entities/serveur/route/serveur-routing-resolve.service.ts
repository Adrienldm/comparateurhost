import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IServeur } from '../serveur.model';
import { ServeurService } from '../service/serveur.service';

@Injectable({ providedIn: 'root' })
export class ServeurRoutingResolveService implements Resolve<IServeur | null> {
  constructor(protected service: ServeurService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IServeur | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((serveur: HttpResponse<IServeur>) => {
          if (serveur.body) {
            return of(serveur.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
