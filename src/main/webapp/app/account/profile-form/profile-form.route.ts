import { Route } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';

import { ProfileFormComponent } from './profile-form.component';

export const profileFormRoute: Route = {
  path: 'profile1',
  component: ProfileFormComponent,
  data: {
    pageTitle: 'global.menu.account.profileForm',
  },
  canActivate: [UserRouteAccessService],
};
