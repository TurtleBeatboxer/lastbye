import { Route } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PublicProfileComponent } from './public-profile.component';

export const publicProfileRoute: Route = {
  path: 'publicProfile/:id',
  component: PublicProfileComponent,
  data: {
    pageTitle: 'global.menu.account.publicProfile',
  },
};
