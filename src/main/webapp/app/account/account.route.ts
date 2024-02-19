import { Routes } from '@angular/router';

import { activateRoute } from './activate/activate.route';
import { passwordRoute } from './password/password.route';
import { passwordResetFinishRoute } from './password-reset/finish/password-reset-finish.route';
import { passwordResetInitRoute } from './password-reset/init/password-reset-init.route';
import { registerRoute } from './register/register.route';
import { settingsRoute } from './settings/settings.route';
import { profileFormRoute } from './profile-form/profile-form.route';

import { tellUsRoute } from './profile-form/tell-us/tell-us.route';
import { profileFormPictureRoute } from './profile-form/profile-form-picture/profile-form-pircutre.router';

import { settingChildrenRoute } from './settings/settingChildren.routes';
import { codeQrRoute } from './codeQR/code-qr/code-qr.route';
import { recoveryQuestionRoute } from './recovery-question-form/recovery-question-form.route';

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
  codeQrRoute,
  recoveryQuestionRoute,
  ...settingChildrenRoute,
];

export const accountState: Routes = [
  {
    path: '',
    children: ACCOUNT_ROUTES,
  },
];
