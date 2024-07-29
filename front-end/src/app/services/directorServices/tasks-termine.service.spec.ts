import { TestBed } from '@angular/core/testing';

import { TasksTermineService } from './tasks-termine.service';

describe('TasksTermineService', () => {
  let service: TasksTermineService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TasksTermineService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
