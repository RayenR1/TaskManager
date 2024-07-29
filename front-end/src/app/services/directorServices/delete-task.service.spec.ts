import { TestBed } from '@angular/core/testing';

import { DeleteTaskService } from './delete-task.service';

describe('DeleteTaskService', () => {
  let service: DeleteTaskService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DeleteTaskService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
