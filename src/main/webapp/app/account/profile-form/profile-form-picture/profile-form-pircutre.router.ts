import { Route } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ProfileFormPictureComponent } from './profile-form-picture.component';

export const profileFormPictureRoute: Route = {
  path: 'picture',
  component: ProfileFormPictureComponent,
  data: {
    pageTitle: 'global.menu.account.profileFormPicture',
  },
  canActivate: [UserRouteAccessService],
};
