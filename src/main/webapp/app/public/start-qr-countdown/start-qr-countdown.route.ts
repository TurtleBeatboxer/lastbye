import { Route } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { StartQrCountdownComponent } from './start-qr-countdown.component';

export const startQrCountdown: Route = {
  path: 'accountRecovery/:id',
  component: StartQrCountdownComponent,
  data: {
    pageTitle: 'global.menu.account.StartQrCountdown',
  },
};
