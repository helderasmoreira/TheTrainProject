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
    
    from = Stop.where(:location => params[:from]).first
    to = Stop.where(:location => params[:to]).first

    routes_from = RouteStop.where(:stop_id => from.id)
    routes_to = RouteStop.where(:stop_id => to.id)

    routes_possible = []

    # Direct routes
    routes_from.each do |route_from|
      routes_to.each do |route_to|
        if route_from.route_id == route_to.route_id and route_from.stop_order < route_to.stop_order
          routes_possible.push([route_from.route_id])
        end
      end  
    end

    if routes_possible.length == 0
      # Indirect routes
      routes_from.each do |route_from|
        stops_of_route_from = RouteStop.where(:route_id => route_from.route_id)
        stops_of_route_from.each do |stop_of_route_from|
          stop = stop_of_route_from.stop_id
          routes_of_stop_of_route_from = RouteStop.where(:stop_id => stop)
          routes_of_stop_of_route_from.each do |route_of_stop_of_route_from|
            stops_of_route_of_stop_of_route_from = RouteStop.where(:route_id => route_of_stop_of_route_from.route_id)
            stops_of_route_of_stop_of_route_from.each do |stop_of_route_of_stop_of_route_from|
              if stop_of_route_of_stop_of_route_from.stop_id == to.id  and route_of_stop_of_route_from.stop_order < stop_of_route_of_stop_of_route_from.stop_order  and route_from.stop_order < route_of_stop_of_route_from.stop_order
                if route_from.route_id != route_of_stop_of_route_from.route_id and routes_possible.include?(route_from.route_id + route_of_stop_of_route_from.route_id*0.1) == false
                  routes_possible.push([route_from.route_id, route_of_stop_of_route_from.stop_id, route_of_stop_of_route_from.route_id])
                end
              end
            end
          end
        end
      end
    end

    routes = []
    logger.info(routes_possible)
    # Process results
    routes_possible.each do |route|
      if route.length == 1
        routes.push(["SIMPLE_ROUTE"] + calculate_routes(from, to, route[0]))
      else
        route1 = calculate_routes(from, Stop.find(route[1]), route[0])
        route2 = calculate_routes(Stop.find(route[1]), to, route[2])
        
        route1_time = Time.parse(route1[-1][-1][1])
        route2_time = Time.parse(route2[1][0][1])

        if route1_time > route2_time
          routes.push(["DUAL_ROUTE_OTHER_DAY", route1[0] + route2[0], route1 + route2])
        else
          routes.push(["DUAL_ROUTE_SAME_DAY", route1[0] + route2[0], route1 + route2])
        end

        #route_final = []
        #route_final.push(route1[0] + route2[0])
        #route_final.push(route1[1].concat(route2[1]))
        #routes.push(route_final)
      end
    end

    respond_to do |format|
      format.json { render json: routes }
    end
  end

  def calculate_routes(from, to, route_id)
    logger.info(from.id)
    logger.info(to.id)
    logger.info(route_id)
    price_per_km = Price.first.price
    stops = []
    route_stop = RouteStop.where(:route_id => route_id)
    route_info = Route.find(route_id)
    first = false
    price = [0.0]
    previous = nil
    route_stop = route_stop.sort_by {|obj| obj.stop_order}
    route_stop.each do |stop|
      temp_stop = Stop.where(:id => stop.stop_id).first

      if first == false and temp_stop.location == from.location
        first = true
      end

      if first == true
        stops.push([temp_stop.location, (route_info.starts + stop.delay*60).to_s(:time) ])
      end
      
      if previous != nil
        d = Distance.where(:stop1_id => previous.id, :stop2_id => temp_stop.id).first
        if d == nil
          d = Distance.where(:stop2_id => previous.id, :stop1_id => temp_stop.id).first
        end
        price[0] += d.distance*price_per_km
      end

      previous = temp_stop

      if temp_stop.location == to.location
        break
      end
    end

    price += [stops]

    return price

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
