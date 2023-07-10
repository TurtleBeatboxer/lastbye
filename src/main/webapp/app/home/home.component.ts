import { Component, OnInit, OnDestroy, ChangeDetectionStrategy } from '@angular/core';
import { Router } from '@angular/router';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';

import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/auth/account.model';

@Component({
  selector: 'jhi-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class HomeComponent implements OnInit, OnDestroy {
  account: Account | null = null;

  private readonly destroy$ = new Subject<void>();

  constructor(private accountService: AccountService, private router: Router) {}

  ngOnInit(): void {
    this.callback();
    this.accountService
      .getAuthenticationState()
      .pipe(takeUntil(this.destroy$))
      .subscribe(account => (this.account = account));
  }

  callback() {
    let accordionHeaders = document.getElementsByClassName('accordion-header');
    for (var i = 0; i < accordionHeaders.length; i++) {
      accordionHeaders[i].addEventListener('click', event => {
        let header = event.target as HTMLElement;
        console.log(event);
        console.log(typeof event);
        var accordionContent = header.nextElementSibling as HTMLElement;
        if (accordionContent) {
          if (accordionContent.style.display === 'block') {
            accordionContent.style.display = 'none';
          } else {
            accordionContent.style.display = 'block';
          }
        }
      });
    }
  }

  login(): void {
    this.router.navigate(['/login']);
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
}
