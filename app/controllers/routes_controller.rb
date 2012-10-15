# -*- coding: utf-8 -*-
class RoutesController < ApplicationController
  # GET /routes
  # GET /routes.json
  def index
    @routes = Route.all

    respond_to do |format|
      format.html # index.html.erb
      format.json { render json: @routes }
    end
  end

  def get_route

    # TODO: only get routes from a specific time interval
    
    from = Stop.where(:location => "Valongo").first
    to = Stop.where(:location => "Penafiel").first

    routes_from = RouteStop.where(:stop_id => from.id)
    routes_to = RouteStop.where(:stop_id => to.id)

    routes_possible = []
    
    # Check routes
    routes_from.each do |route_from|
      routes_stops = RouteStop.where(:route_id => route_from.route_id)
      routes_stops.each do |route_stop|
        stop = route_stop.stop_id
        stop_routes = RouteStop.where(:stop_id => stop)
        stop_routes.each do |stop_route|
          stop_routes2 = RouteStop.where(:route_id => stop_route.route_id)
          stop_routes2.each do |stop_route2|
            if stop_route2.stop_id == to.id and stop_route.stop_order < stop_route2.stop_order
              if stop_route.route_id == route_stop.route_id and routes_possible.include?(stop_route.route_id) == false
                routes_possible.push(stop_route.route_id)
              elsif route_stop.route_id != stop_route.route_id and routes_possible.include?(route_stop.route_id + stop_route.route_id*0.1) == false
                routes_possible.push(route_stop.route_id + stop_route.route_id*0.1)
              end
            end
          end
        end
      end
    end
    
    puts routes_possible
  end

  # GET /routes/1
  # GET /routes/1.json
  def show
    @route = Route.find(params[:id])

    respond_to do |format|
      format.html # show.html.erb
      format.json { render json: @route }
    end
  end

  # GET /routes/new
  # GET /routes/new.json
  def new
    @route = Route.new

    respond_to do |format|
      format.html # new.html.erb
      format.json { render json: @route }
    end
  end

  # GET /routes/1/edit
  def edit
    @route = Route.find(params[:id])
  end

  # POST /routes
  # POST /routes.json
  def create
    @route = Route.new(params[:route])

    respond_to do |format|
      if @route.save
        format.html { redirect_to @route, notice: 'Route was successfully created.' }
        format.json { render json: @route, status: :created, location: @route }
      else
        format.html { render action: "new" }
        format.json { render json: @route.errors, status: :unprocessable_entity }
      end
    end
  end

  # PUT /routes/1
  # PUT /routes/1.json
  def update
    @route = Route.find(params[:id])

    respond_to do |format|
      if @route.update_attributes(params[:route])
        format.html { redirect_to @route, notice: 'Route was successfully updated.' }
        format.json { head :no_content }
      else
        format.html { render action: "edit" }
        format.json { render json: @route.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /routes/1
  # DELETE /routes/1.json
  def destroy
    @route = Route.find(params[:id])
    @route.destroy

    respond_to do |format|
      format.html { redirect_to routes_url }
      format.json { head :no_content }
    end
  end
end
