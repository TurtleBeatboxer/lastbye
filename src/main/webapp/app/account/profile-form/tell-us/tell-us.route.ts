import { Route } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TellUsComponent } from './tell-us.component';

export const tellUsRoute: Route = {
  path: 'tell-us',
  component: TellUsComponent,
  data: {
    pageTitle: 'global.menu.account.tellUsComponent',
  },
  canActivate: [UserRouteAccessService],
};
