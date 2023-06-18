import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';
import { PasswordStrengthBarComponent } from './password/password-strength-bar/password-strength-bar.component';
import { RegisterComponent } from './register/register.component';
import { ActivateComponent } from './activate/activate.component';
import { PasswordComponent } from './password/password.component';
import { PasswordResetInitComponent } from './password-reset/init/password-reset-init.component';
import { PasswordResetFinishComponent } from './password-reset/finish/password-reset-finish.component';
import { SettingsComponent } from './settings/settings.component';
import { accountState } from './account.route';
import {
  NbButtonModule,
  NbCardModule,
  NbDatepickerModule,
  NbInputModule,
  NbLayoutModule,
  NbRadioModule,
  NbStepperModule,
} from '@nebular/theme';
import { MatButtonModule } from '@angular/material/button';

import { ProfileFormComponent } from './profile-form/profile-form.component';
@NgModule({
  imports: [
    SharedModule,
    RouterModule.forChild(accountState),
    NbButtonModule,
    NbStepperModule,
    NbCardModule,
    MatButtonModule,
    NbLayoutModule,
    NbRadioModule,
    NbInputModule,
    NbDatepickerModule,
  ],
  declarations: [
    ActivateComponent,
    RegisterComponent,
    PasswordComponent,
    PasswordStrengthBarComponent,
    PasswordResetInitComponent,
    PasswordResetFinishComponent,
    SettingsComponent,
    ProfileFormComponent,
  ],
})
export class AccountModule {}
