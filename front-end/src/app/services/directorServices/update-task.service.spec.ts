import { TestBed } from '@angular/core/testing';

import { UpdateTaskService } from './update-task.service';

describe('UpdateTaskService', () => {
  let service: UpdateTaskService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UpdateTaskService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
