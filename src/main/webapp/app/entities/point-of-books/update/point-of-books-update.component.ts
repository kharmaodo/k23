import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IPointOfBooks, PointOfBooks } from '../point-of-books.model';
import { PointOfBooksService } from '../service/point-of-books.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { StatusOfPOB } from 'app/entities/enumerations/status-of-pob.model';
import { Category } from 'app/entities/enumerations/category.model';

@Component({
  selector: 'jhi-point-of-books-update',
  templateUrl: './point-of-books-update.component.html',
})
export class PointOfBooksUpdateComponent implements OnInit {
  isSaving = false;
  statusOfPOBValues = Object.keys(StatusOfPOB);
  categoryValues = Object.keys(Category);

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    content: [null, [Validators.required]],
    contentContentType: [],
    thumbnail: [],
    price: [],
    status: [],
    volume: [],
    author: [],
    category: [],
    createdAt: [],
    deleted: [],
  });

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected pointOfBooksService: PointOfBooksService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pointOfBooks }) => {
      if (pointOfBooks.id === undefined) {
        const today = dayjs().startOf('day');
        pointOfBooks.createdAt = today;
      }

      this.updateForm(pointOfBooks);
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(new EventWithContent<AlertError>('k23App.error', { ...err, key: 'error.file.' + err.key })),
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const pointOfBooks = this.createFromForm();
    if (pointOfBooks.id !== undefined) {
      this.subscribeToSaveResponse(this.pointOfBooksService.update(pointOfBooks));
    } else {
      this.subscribeToSaveResponse(this.pointOfBooksService.create(pointOfBooks));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPointOfBooks>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(pointOfBooks: IPointOfBooks): void {
    this.editForm.patchValue({
      id: pointOfBooks.id,
      name: pointOfBooks.name,
      content: pointOfBooks.content,
      contentContentType: pointOfBooks.contentContentType,
      thumbnail: pointOfBooks.thumbnail,
      price: pointOfBooks.price,
      status: pointOfBooks.status,
      volume: pointOfBooks.volume,
      author: pointOfBooks.author,
      category: pointOfBooks.category,
      createdAt: pointOfBooks.createdAt ? pointOfBooks.createdAt.format(DATE_TIME_FORMAT) : null,
      deleted: pointOfBooks.deleted,
    });
  }

  protected createFromForm(): IPointOfBooks {
    return {
      ...new PointOfBooks(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      contentContentType: this.editForm.get(['contentContentType'])!.value,
      content: this.editForm.get(['content'])!.value,
      thumbnail: this.editForm.get(['thumbnail'])!.value,
      price: this.editForm.get(['price'])!.value,
      status: this.editForm.get(['status'])!.value,
      volume: this.editForm.get(['volume'])!.value,
      author: this.editForm.get(['author'])!.value,
      category: this.editForm.get(['category'])!.value,
      createdAt: this.editForm.get(['createdAt'])!.value ? dayjs(this.editForm.get(['createdAt'])!.value, DATE_TIME_FORMAT) : undefined,
      deleted: this.editForm.get(['deleted'])!.value,
    };
  }
}
