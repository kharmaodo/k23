import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PointOfBooksComponent } from './list/point-of-books.component';
import { PointOfBooksDetailComponent } from './detail/point-of-books-detail.component';
import { PointOfBooksUpdateComponent } from './update/point-of-books-update.component';
import { PointOfBooksDeleteDialogComponent } from './delete/point-of-books-delete-dialog.component';
import { PointOfBooksRoutingModule } from './route/point-of-books-routing.module';

@NgModule({
  imports: [SharedModule, PointOfBooksRoutingModule],
  declarations: [PointOfBooksComponent, PointOfBooksDetailComponent, PointOfBooksUpdateComponent, PointOfBooksDeleteDialogComponent],
  entryComponents: [PointOfBooksDeleteDialogComponent],
})
export class PointOfBooksModule {}
