import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';
import { HOME_ROUTE } from './home.route';
import { HomeComponent } from './home.component';
import { NgbAccordionModule } from '@ng-bootstrap/ng-bootstrap';
import { NbAccordionModule } from '@nebular/theme';

@NgModule({
  imports: [SharedModule, RouterModule.forChild([HOME_ROUTE]), NbAccordionModule],
  declarations: [HomeComponent],
})
export class HomeModule {}
