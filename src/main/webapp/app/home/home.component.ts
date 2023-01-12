import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { Subject } from 'rxjs';
import { filter, takeUntil } from 'rxjs/operators';

import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/auth/account.model';
import { EntityArrayResponseType, ServeurService } from 'app/entities/serveur/service/serveur.service';
import { IServeur } from 'app/entities/serveur/serveur.model';

@Component({
  selector: 'jhi-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent implements OnInit, OnDestroy {
  account: Account | null = null;
  serveurs!: IServeur[] | null;
  hebergeursList!: String[] | null;
  typesinfra!: String[] | null;
  nbCpus!: number[] | null;
  rams!: number[] | null;
  types!: String[] | null;
  bandWidths!: number[] | null;
  serveursCompare!: IServeur[] | null;
  selectedhebergeur!: String | null;
  selectedCPU!: number | null;
  selectedinfra!: String | null;
  selectedRAM!: number | null;
  selectedType!: String | null;
  selectedBandWidth!: String | null;

  private readonly destroy$ = new Subject<void>();

  constructor(private accountService: AccountService, private serveurService: ServeurService, private router: Router) {}

  ngOnInit(): void {
    this.accountService
      .getAuthenticationState()
      .pipe(takeUntil(this.destroy$))
      .subscribe(account => (this.account = account));
    this.serveurService.query().subscribe({
      next: (res: EntityArrayResponseType) => {
        this.onResponseSuccess(res);
      },
    });
  }

  onResponseSuccess(res: EntityArrayResponseType): void {
    this.serveurs = res.body;
    this.hebergeursList = this.serveurs!.map(a => a.nomHebergeur!).filter((v, i, a) => a.indexOf(v) === i);
    this.typesinfra = this.serveurs!.map(a => a.arch!).filter((v, i, a) => a.indexOf(v) === i);
    this.nbCpus = this.serveurs!.map(a => a.cpuNombre!)
      .filter((v, i, a) => a.indexOf(v) === i)
      .sort((n1, n2) => n1 - n2);
    this.rams = this.serveurs!.map(a => a.ram!)
      .filter((v, i, a) => a.indexOf(v) === i)
      .sort((n1, n2) => n1 - n2);
    this.types = this.serveurs!.map(a => a.type!).filter((v, i, a) => a.indexOf(v) === i);
    this.bandWidths = this.serveurs!.map(a => a.bandWidthInternal!)
      .filter((v, i, a) => a.indexOf(v) === i)
      .sort((n1, n2) => n1 - n2);
  }

  onlyUnique(value: number, index: number, self: any) {
    return self.indexOf(value) === index;
  }

  login(): void {
    this.router.navigate(['/login']);
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  compare(): void {
    this.serveurService
      .query({
        'nomHebergeur.equals': this.selectedhebergeur,
        'cpuNombre.equals': this.selectedCPU,
        'arch.equals': this.selectedinfra,
        'selectedRAM.equals': this.selectedRAM,
        'selectedType.equals': this.selectedType,
        'selectedBandWidth.equals': this.selectedBandWidth,
      })
      .subscribe({
        next: (res: EntityArrayResponseType) => {
          this.onResponseSuccessCompare(res);
        },
      });
  }

  onResponseSuccessCompare(res: EntityArrayResponseType): void {
    this.serveursCompare = res.body;
    this.serveursCompare = this.serveursCompare!.sort((n1, n2) => n1.hourlyPrice! - n2.hourlyPrice!);
  }
}
