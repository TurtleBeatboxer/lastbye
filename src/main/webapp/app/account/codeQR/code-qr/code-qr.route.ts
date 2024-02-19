import { Route } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';

import { CodeQrComponent } from './code-qr.component';

export const codeQrRoute: Route = {
  path: 'codeQr',
  component: CodeQrComponent,
  data: {
    pageTitle: 'global.menu.account.codeQrComponent',
  },
  canActivate: [UserRouteAccessService],
};
