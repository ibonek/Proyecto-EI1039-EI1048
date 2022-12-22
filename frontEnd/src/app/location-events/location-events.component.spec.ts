import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LocationEventsComponent } from './location-events.component';

describe('LocationEventsComponent', () => {
  let component: LocationEventsComponent;
  let fixture: ComponentFixture<LocationEventsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LocationEventsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LocationEventsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
