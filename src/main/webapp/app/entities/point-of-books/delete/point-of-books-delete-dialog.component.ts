import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPointOfBooks } from '../point-of-books.model';
import { PointOfBooksService } from '../service/point-of-books.service';

@Component({
  templateUrl: './point-of-books-delete-dialog.component.html',
})
export class PointOfBooksDeleteDialogComponent {
  pointOfBooks?: IPointOfBooks;

  constructor(protected pointOfBooksService: PointOfBooksService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.pointOfBooksService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
