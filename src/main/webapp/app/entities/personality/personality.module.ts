import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PersonalityComponent } from './list/personality.component';
import { PersonalityDetailComponent } from './detail/personality-detail.component';
import { PersonalityUpdateComponent } from './update/personality-update.component';
import { PersonalityDeleteDialogComponent } from './delete/personality-delete-dialog.component';
import { PersonalityRoutingModule } from './route/personality-routing.module';

@NgModule({
  imports: [SharedModule, PersonalityRoutingModule],
  declarations: [PersonalityComponent, PersonalityDetailComponent, PersonalityUpdateComponent, PersonalityDeleteDialogComponent],
})
export class PersonalityModule {}
