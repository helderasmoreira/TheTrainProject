class RouteStop < ActiveRecord::Base
  belongs_to :route
  belongs_to :stop
  validates_uniqueness_of :route_id, :scope => :stop_id
  validates_uniqueness_of :route_id, :scope => :stop_order
  attr_accessible :delay, :stop_order, :route_id, :stop_id
end
