import { Route } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { SettingsComponent } from './settings.component';

export const settingsRoute: Route = {
  path: 'profile',
  component: SettingsComponent,
  data: {
    pageTitle: 'global.menu.account.settings',
  },
  canActivate: [UserRouteAccessService],
};
