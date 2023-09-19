import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { SharedModule } from 'app/shared/shared.module';
import { HOME_ROUTE } from './home.route';
import { HomeComponent } from './home.component';
import { NbCardModule, NbDatepickerModule, NbInputModule, NbLayoutModule } from '@nebular/theme';
import { NavbarComponent } from 'app/layouts/navbar/navbar.component';
@NgModule({
  imports: [SharedModule, RouterModule.forChild([HOME_ROUTE]), NbCardModule, NbDatepickerModule, NbInputModule, NbLayoutModule],
  declarations: [HomeComponent],
})
export class HomeModule {}
