import { Routes } from '@angular/router';

import { activateRoute } from './activate/activate.route';
import { passwordRoute } from './password/password.route';
import { passwordResetFinishRoute } from './password-reset/finish/password-reset-finish.route';
import { passwordResetInitRoute } from './password-reset/init/password-reset-init.route';
import { registerRoute } from './register/register.route';
import { settingsRoute } from './settings/settings.route';
import { profileFormRoute } from './profile-form/profile-form.route';
import { publicProfileRoute } from './public-profile/public-profile.route';
import { tellUsRoute } from './profile-form/tell-us/tell-us.route';
import { profileFormPictureRoute } from './profile-form/profile-form-picture/profile-form-pircutre.router';

const ACCOUNT_ROUTES = [
  activateRoute,
  passwordRoute,
  passwordResetFinishRoute,
  passwordResetInitRoute,
  registerRoute,
  settingsRoute,
  profileFormRoute,
  profileFormPictureRoute,
  tellUsRoute,
];

export const accountState: Routes = [
  {
    path: '',
    children: ACCOUNT_ROUTES,
  },
];
