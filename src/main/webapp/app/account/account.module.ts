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
import { PublicProfileComponent } from './public-profile/public-profile.component';
import { CodeQrComponent } from './codeQR/code-qr/code-qr.component';
import { TellUsComponent } from './profile-form/tell-us/tell-us.component';
import { ProfileFormPictureComponent } from './profile-form/profile-form-picture/profile-form-picture.component';
import { RelativeComponent } from './profile-form/relative/relative.component';
import { BasicInfoEditComponent } from './settings/basic-info-edit/basic-info-edit.component';
import { SecurityEditComponent } from './settings/security-edit/security-edit.component';
import { MyBurialEditComponent } from './settings/my-burial-edit/my-burial-edit.component';
import { MyFuneralEditComponent } from './settings/my-funeral-edit/my-funeral-edit.component';
import { MyLastMessageEditComponent } from './settings/my-last-message-edit/my-last-message-edit.component';
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
    NbDatepickerModule.forRoot(),
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
    PublicProfileComponent,
    CodeQrComponent,
    TellUsComponent,
    ProfileFormPictureComponent,
    RelativeComponent,
    BasicInfoEditComponent,
    SecurityEditComponent,
    MyBurialEditComponent,
    MyFuneralEditComponent,
    MyLastMessageEditComponent,
  ],
})
export class AccountModule {}
