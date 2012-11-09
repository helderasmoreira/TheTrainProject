# -*- coding: utf-8 -*-
class TicketsController < ApplicationController
  # GET /tickets
  # GET /tickets.json
  def index
    @tickets = Ticket.all

    respond_to do |format|
      format.html # index.html.erb
      format.json { render json: @tickets }
    end
  end

  def new_ticket
    ticket = Ticket.create(:price => params[:price], 
      :user_id => params[:user_id], 
      :paid => false,
      :departureTime => params[:departureTime],
      :arrivalTime => params[:arrivalTime],
      :duration => params[:duration],
      :departure => params[:departure],
      :arrival => params[:arrival],
      :date => params[:date])
    respond_to do |format|
      format.json { render json: [ticket.id] }
    end
  end

  def verify_ticket
    route_id = params[:route_id]
    stop_start = params[:stop_start]
    stop_end = params[:stop_end]
    date = params[:date]
    date = Time.parse(date).strftime("%Y-%m-%d")

    date_now = Time.now.strftime("%Y-%m-%d")

    if date == date_now
      t = Route.find(params[:route_id]).starts
      t2 = Time.now
      t1 = t.change(:month => 1, :day => 1, :year => 2000)
      t2 = t2.change(:month => 1, :day => 1, :year => 2000)
      if t <= t2
        respond_to do |format|
          format.json { render json: ["no"] }
        end
        return
      end
    end 

    stop_start_order = RouteStop.where(:route_id => route_id, :stop_id => Stop.where(:location => stop_start).first).first.stop_order
    stop_end_order = RouteStop.where(:route_id => route_id, :stop_id => Stop.where(:location => stop_end).first).first.stop_order

    tickets_from_route = TicketRoute.where(["route_id = ? and stop_start_order >= ? and stop_end_order <= ? and date = ?", route_id, stop_start_order, stop_end_order, date])

    response = ["yes"]
    response[1] = 200 - tickets_from_route.length
    if tickets_from_route.length > 200
     response[0] = "no"
    end

    respond_to do |format|
      format.json { render json: response }
    end

  end

  def getByUserId

    tickets = Ticket.where(["user_id = ? AND paid = ?", params[:user_id], false])

    tickets.each do |ticket|
      t1 = Time.now
      t2 = Time.parse(ticket.date)
      t3 = Time.parse(ticket.departureTime)
      t4 = Time.utc(t2.year, t2.month, t2.day, t3.hour, t3.min)
      t1 = t1 + 1.day

      if t4 <= t1
        ticket.destroy
        ticket_routes = TicketRoute.where(:ticket_id => ticket.id)
        
        ticket_routes.each do |ticket_route|
          ticket_route.destroy
        end
      end
    end

    tickets = Ticket.where(["user_id = ? AND paid = ?", params[:user_id], false])


    respond_to do |format|
      format.json { render json: tickets }
    end
  end


  # GET /tickets/1
  # GET /tickets/1.json
  def show
    @ticket = Ticket.find(params[:id])

    respond_to do |format|
      format.html # show.html.erb
      format.json { render json: @ticket }
    end
  end

  # GET /tickets/new
  # GET /tickets/new.json
  def new
    @ticket = Ticket.new

    respond_to do |format|
      format.html # new.html.erb
      format.json { render json: @ticket }
    end
  end

  # GET /tickets/1/edit
  def edit
    @ticket = Ticket.find(params[:id])
  end

  # POST /tickets
  # POST /tickets.json
  def create
    @ticket = Ticket.new(params[:ticket])

    respond_to do |format|
      if @ticket.save
        format.html { redirect_to @ticket, notice: 'Ticket was successfully created.' }
        format.json { render json: @ticket, status: :created, location: @ticket }
      else
        format.html { render action: "new" }
        format.json { render json: @ticket.errors, status: :unprocessable_entity }
      end
    end
  end

  # PUT /tickets/1
  # PUT /tickets/1.json
  def update
    @ticket = Ticket.find(params[:id])

    respond_to do |format|
      if @ticket.update_attributes(params[:ticket])
        format.html { redirect_to @ticket, notice: 'Ticket was successfully updated.' }
        format.json { head :no_content }
      else
        format.html { render action: "edit" }
        format.json { render json: @ticket.errors, status: :unprocessable_entity }
      end
    end
  end

  def pay
    ticket = Ticket.find(params[:id])
    result = ["paid"]

    if Random.rand(200) > 150
      result = ["error"]
    else
      ticket.paid = true
      ticket.save
    end

    respond_to do |format|
        format.json { render json: result}
    end
  end

  def cancel
    @ticket = Ticket.find(params[:id])
    @ticket.destroy

    ticket_routes = TicketRoute.where(:ticket_id => params[:id])
    ticket_routes.each do |ticket_route|
      ticket_route.destroy
    end

    render :nothing => true
  end

  # DELETE /tickets/1
  # DELETE /tickets/1.json
  def destroy
    @ticket = Ticket.find(params[:id])
    @ticket.destroy

    respond_to do |format|
      format.html { redirect_to tickets_url }
      format.json { head :no_content }
    end
  end
end
